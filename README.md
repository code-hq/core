# CodeHQ

## Getting Started

### Docker

#### Build

To build the project using docker, you just need to enable the `docker` maven profile when packaging:

```bash
./mvnw package -P docker
```

You can also enable the `docker` profile from IntelliJ via the `Maven Projects` tool window under the `Profiles` tree.

This command will compile, test and package the application, then copy the resulting `.jar` into a new docker image
which will automatically be tagged with the release number as well as `latest`.

#### Run

To run the project via the docker images, you can use `docker-compose`:

```bash
docker-compose up -d
```

Alternatively, if you want to run the application from IntelliJ, you can start up just the infrastructure containers
such as the database by specifying the corresponding `docker-compose` config file:

```bash
docker-compose -f docker-compose.infra.yml up -d
```