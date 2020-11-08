use amiquip::{
    Connection, ConsumerMessage, ConsumerOptions, ExchangeDeclareOptions, ExchangeType, FieldTable,
    QueueDeclareOptions, Result,
};
use serde::{Deserialize, Serialize};

use std::{env, thread};
use kv::*;
use std::sync::mpsc;
use std::borrow::Cow;


#[derive(Serialize, Deserialize, Debug)]
enum PollStatus {
    OPEN,
    CLOSED
}
/*
( 1) FEEDAPP :
{
"id":"ea8eaedd-b809-46fb-b429-41829b1352ff ",
"name":"kjasdkjfhaskfj ANders er kul",
"question":"whaaat===",
"timestart":1604100676441,
"timeend":1604100651276,
"access":"PUBLIC",
"answeryes":"asdfasdfasdfasdf",
"answerno":"asdfasdf",
"owner":{
    "id":"892813d3-ceed-4a22-9dbe-bf6110e80e5e",
    "firstname":"PollHub",
    "lastname":"Admin"}
 }
 */
#[derive(Serialize, Deserialize, Debug)]
struct Poll {
    id: String,
    name: String,
    question: String,
    timestart: i64,
    timeend: i64,
    access: String,
    answeryes: String,
    answerno: String,
    owner : Owner
}

#[derive(Serialize, Deserialize, Debug)]
struct Owner {
    id: String,
    firstname: String,
    lastname: String
}

#[derive(Serialize, Deserialize, Debug)]
struct PollEntry {
    id: String,
    endtime: i64
}
#[derive(Serialize, Deserialize, Debug)]
struct DweetPoll {
    id: String,
    status: PollStatus
}



#[tokio::main]
async fn main() {

    let server = env::var("RABBITSERVER").unwrap_or("localhost".to_string());
    let user = env::var("RABBITUSER").unwrap_or("guest".to_string());
    let pass = env::var("RABBITPASSWORD").unwrap_or("guest".to_string());
    let routingkey = env::var("RABBITROUTINGKEY").unwrap_or("FEEDAPP".to_string());
    let cfg = Config::new("jobs");
    let store = Store::new(cfg).unwrap();
    let _db = store.bucket::<&str, Json<PollEntry>>(None).unwrap();

    // db Thread?

    process(server,user,pass,routingkey).await.unwrap_err();

}




async fn process(server: String, user: String, pass: String, routingkey: String) -> Result<()> {
    let connection = format!(
        "{:?}",
        format_args!(
            "amqp://{u}:{p}@{con}:5672",
            con = server,
            u = user,
            p = pass
        )
    );

    print!("[x] {}", connection.to_string());
    let con = Connection::insecure_open(&connection);
    let mut con = match con {
        Ok(con) => con,
        Err(error) => panic!("Problem opening connection: {}", error),
    };
    let channel = con.open_channel(None)?;

    let exchange = channel.exchange_declare(
        ExchangeType::Topic,
        "feedapp.topic.exchange",
        ExchangeDeclareOptions::default(),
    )?;

    let que = channel.queue_declare(
        "",
        QueueDeclareOptions {
            exclusive: true,
            ..QueueDeclareOptions::default()
        },
    )?;

    println!("[-] Que is ready {}", que.name());
    que.bind(&exchange, routingkey, FieldTable::new())?;

    let consumer = que.consume(ConsumerOptions {
        no_ack: true,
        ..ConsumerOptions::default()
    })?;

    println!("[>] Waiting for Polls");

    for (i, msg) in consumer.receiver().iter().enumerate() {
        match msg {
            ConsumerMessage::Delivery(delivery) => {
                let body = String::from_utf8_lossy(&delivery.body);
                println!("({:3}) {} : {}", i, delivery.routing_key, body);

                let p = json_to_object(&body);
                if p.is_ok() {
                    let (tx, rx) = mpsc::channel();
                    thread::spawn(move || {
                        // lets start working on this job here.
                        // send the data to dweet.io
                        println!("[> Thr] Thread spawned");
                        let id = rx.recv().unwrap();
                        println!("[| Thr: {}", &id);
                        let dw = DweetPoll { id: id, status: PollStatus::OPEN };

                        let url = "https://dweet.io/dweet/for/be4106c3-bd56-40ca-9a5a-c1cb0c0bf8cc";
                        println!("[| posting: {}", url);
                        let resp = ureq::post(url)
                            .send_json(serde_json::json!(dw));

                        if resp.ok() {
                            println!("success: {}", resp.into_string().unwrap());
                        } else {
                            println!("error {}: {}", resp.status(), resp.into_string().unwrap());
                        }
                        println!("[> Thr] Thread closed");
                    });
                    tx.send(p.unwrap().id).unwrap();

                } else {
                    println!("({:3}) {} : {} => {}", i, delivery.routing_key, "Incorrect msg", &body);
                }
            }
            other => {
                println!("[Q] Other msg {:?}", other);
                break;
            }
        }
    }

    con.close()?;
    Ok(())
}

fn json_to_object(body: &Cow<str>) -> Result<Poll, serde_json::Error> {
    let p = ::serde_json::from_str(&body);
    let p = match p {
        Ok(p) => p,
        Err(e) => return Err(e),
    };
    return Ok(p);
}
