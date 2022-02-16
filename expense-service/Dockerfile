
FROM rust:1.58.1 as builder
#FROM rustlang/rust:nightly as builder
FROM liuchong/rustup
ARG port=8000
RUN rustup default stable
ADD src /app
WORKDIR /app
COPY . .
RUN cargo build --release
EXPOSE $port
# COPY ./Cargo.lock ./Cargo.lock
# COPY ./Cargo.toml ./Cargo.toml
# RUN cargo build --release
#RUN rustup default stable
#RUN rustup default nightly 
#RUN cargo build
#CMD ["cargo", "run"]
#TODO: fix this file

# FROM alpine:latest
# COPY --from=builder /usr/local/cargo/bin/.  /usr/local/cargo/bin/expense-service
# ENV ROCKET_ADDRESS=0.0.0.0
# ENV ROCKET_PORT=$port
# EXPOSE $port
# CMD ["expense-service"]
