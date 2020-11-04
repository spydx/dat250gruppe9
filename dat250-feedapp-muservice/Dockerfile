FROM rust:1.40 as builder

WORKDIR /app

COPY . .

ARG RABBITSERVER
ENV RABBITSERVER=${RABBITSERVER}}
ENV RABBITUSER={{RABBITUSER}}
ENV RABBITPASSWORD={{RABBITPASSWORD}}
ENV RABBITROUTINGKEY={{RABBITROUTINGKEY}}

RUN cargo install --path .

FROM debian:buster-slim
RUN apt-get update && rm -rf /var/lib/apt/lists/*
COPY --from=builder /usr/local/cargo/bin/dat250-feedapp-muservice /usr/local/bin/dat250-feedapp-muservice
CMD ["dat250-feedapp-muservice"]