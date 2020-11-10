db.createUser(
   {
       user: "feedapp",
       pwd: "mongo",
       roles: [
           {
               role: "readWrite",
               db: "feedapp"
           }
       ]
   }
);
db.createCollection("resultcol");