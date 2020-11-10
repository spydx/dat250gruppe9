
# muService in Rust 

## dweet.io details
UUID to ID all dweet.io objects

thing = be4106c3-bd56-40ca-9a5a-c1cb0c0bf8cc

### Opening post
POST dweet.io/dweet/for/{thing}
Content-type: application/json

{
    "id": "guid for poll"
    "status" : "open"
    
}

### Closing post
POST dweet.io/dweet/for/{thing}
Content-type: application/json

{
    "id": "guid for poll"
    "status" : "closed"
}

## Task stored in database
- "id":  "guid to poll"
- "endtime": int

Fetch result and dump at mongodb.
