![KMITL OSRS](https://zartre.com/files/KMITL-OSRS.png)
# Panda API

An API of **KMITL Online Space Reservation System**, aka _Panda Project_.

Try it on https://space.itforge.io/graphiql

# Contributing

This instruction assumes you already have brew and docker installed

## Prerequisite

1. Install [sbt](https://www.scala-sbt.org)
```bash
$ brew install sbt
```

## Start the Server

1. Start PostgreSQL
```
$ make start
```

2. Create local development database
```
$ make createdb
```

3. Start the server.
```
$ sbt run
```

The API can now be accessed at `localhost:9000`.

## Project Structure

```
panda-api/
├── app/
│   ├── config/             <-- app configuration files
│   ├── controllers/        <-- REST API controllers
│   ├── definitions/        <-- app definitions e.g. exceptions, constants
│   ├── entities/           <-- database entities, they have exactly same structure as database tables
│   ├── facades/            <-- application logic
│   ├── models/             <-- GraphQL models
│   ├── persists/           <-- database interfaces and implementations
│   ├── schemas/            <-- GraphQL schemas
│   ├── services/           <-- outside services e.g. LDAP, Facebook authentication
│   ├── utils/              <-- utilities
│   └── validators/         <-- validation logic
├── conf/
│   ├── application.conf    <-- default application configuration
│   ├── base.conf           <-- base configuration
│   ├── production.conf     <-- production configuration
│   ├── evolutions/         <-- database DDL scripts
│   └── routes/             <-- route file
├── project/
│   ├── build.properties    <-- sbt version used in this project
│   └── plugins.sbt         <-- sbt plugins
├── script/                 <-- script files
├── test/                   <-- tests
└── build.sbt               <-- sbt build file, which contains application dependencies, plugins and tasks
```

# Team Members
We are from Information Technology, King Mongkut's Institute of Technology Ladkrabang

||First Name|Last Name|GitHub Username|Student ID|
|:-:|--|------|---------------|---------|
|<img src="https://avatars1.githubusercontent.com/u/20960087" width="75px">|Kavin|Ruengprateepsang|[@kavinvin](https://github.com/kavinvin)|59070009|
|<img src="https://avatars3.githubusercontent.com/u/13056824" width="75px">|Kunanon|Srisuntiroj|[@sagelga](https://github.com/sagelga)|59070022|
|<img src="https://avatars2.githubusercontent.com/u/22119886" width="75px">|Thitipat|Worrarat|[@ynhof6](https://github.com/ynhof6)|59070043|
|<img src="https://avatars0.githubusercontent.com/u/3814520" width="75px">|Nathan|Yiangsupapaanontr|[@DobaKung](https://github.com/DobaKung)|59070087|
|<img src="https://avatars1.githubusercontent.com/u/20330195" width="75px">|Pornprom|Kiawjak|[@foofybuster](https://github.com/foofybuster)|59070113|

The project is part of these subjects:
* 06016215 Web Programming
* 06016216 Information System and Analysis
* 06016217 Database System Concepts
