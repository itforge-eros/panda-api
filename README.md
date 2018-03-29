# Panda API

Online Space Reservation System GraphQL API

Develop Branch[![Build Status](https://travis-ci.com/itforge-eros/panda-api.svg?token=hxfRmfpCpbnunWcyMpkC&branch=develop)](https://travis-ci.com/itforge-eros/panda-api)

# Installation

Install sbt
```shell
$ brew install sbt
```

Install PostgreSQL
```shell
$ brew install postgresql
```

Create local development database
```shell
$ ./script/setup-database development
```

# Running the service
```shell
sbt
test
```
This will local host the service. A port of the localhost will be given.
