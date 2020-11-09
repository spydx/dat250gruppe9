FROM rust:1.40 as builder

WORKDIR /app

COPY . .

ARG RABBITSERVER
ENV RABBITSERVER=${RABBITSERVER}}
ENV RABBITUSER={{RABBITUSER}}
ENV RABBITPASSWORD={{RABBITPASSWORD}}
ENV RABBITROUTINGKEY={{RABBITROUTINGKEY}}
ENV MONGO_SERVER={{MONGO_SERVER}}

RUN cargo install --path .

FROM debian:bullseye-slim
#RUN apt-get update && rm -rf /var/lib/apt/lists/*
COPY --from=builder /usr/local/cargo/bin/dat250-feedapp-muservice /usr/local/bin/dat250-feedapp-muservice
CMD ["dat250-feedapp-muservice"]
