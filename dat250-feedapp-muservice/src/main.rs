use amiquip::{
    Connection, ConsumerMessage, ConsumerOptions, ExchangeDeclareOptions, ExchangeType, FieldTable,
    QueueDeclareOptions, Result,
};
use serde::{Deserialize, Serialize};

use reqwest;
use std::{env, thread};
use kv::*;
use std::sync::mpsc;


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
    let dweet_id = env::var("DWEETID").unwrap_or("be4106c3-bd56-40ca-9a5a-c1cb0c0bf8cc".to_string());
    let debug = env::var("RELEASE").unwrap_or("DEBUG".to_string());
    //static post_url : str = ["https://dweet.io/dweet/for/", dweet_id.as_str()].join("");
    let cfg = Config::new("jobs");
    let store = Store::new(cfg).unwrap();
    let db = store.bucket::<&str, Json<PollEntry>>(None).unwrap();
    if debug == "DEBUG" {
        db.clear();
    }
    /*for job in db.iter() {
        verify(job.unwrap())
    }*/

    process(server,user,pass,routingkey).await.unwrap_err();

}



async fn post_dweetio(dweet: String) -> Result<String> {
    let url = "https://dweet.io/dweet/for/be4106c3-bd56-40ca-9a5a-c1cb0c0bf8cc";
    let json_value = serde_json::to_string(&dweet);
    let client = reqwest::Client::new();
    let res = client
        .post(url)
        .json(&dweet)
        .send().await;
    println!("Dweeted");
    Ok("Success".to_string())

}

async fn process(server: String, user: String, pass: String, routingkey: String) -> Result<()> {
    let (tx, rx) = mpsc::channel();

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

    thread::spawn(move || {
        // lets start working on this job here.
        // send the data to dweet.io
        let dweet = rx.recv().unwrap();
        async {
            let res = post_dweetio(dweet).await;
        }
    });

    for (i, msg) in consumer.receiver().iter().enumerate() {
        match msg {
            ConsumerMessage::Delivery(delivery) => {
                let body = String::from_utf8_lossy(&delivery.body);
                println!("({:3}) {} : {}", i, delivery.routing_key, body);
                // missing error handling
                let p : Poll = ::serde_json::from_str(&body).unwrap();
                let dw = DweetPoll {id: p.id, status: PollStatus::OPEN };
                tx.send((&body).to_string()).unwrap();
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
