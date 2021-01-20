# petclinic-spring-boot

## Usage

### Development

開発時は組み込みDB(H2)を使用して動作確認する。  
Spring boot の Profile を `dev` に設定することにより開発モードで起動する。  
(省略時も `dev` として動作する)

```bash
$ spring_profiles_active=dev ./gradlew bootRun
```

#### OpenApi

springdoc-openapiにより、API定義ファイルは自動生成される。

アプリケーション起動後に、 `http://localhost:8080/swagger-ui.html` にアクセスすることでWeb API 仕様書を確認できる。

### Production

フロントエンドアプリケーションおよび、PostgreSQLと繋いで実行する場合は、以下のように行う。

フロントエンドアプリケーションをビルドする。  

事前準備： 環境で1度だけ実行

```bash
$ npm login --scope=@t-kiyono --registry=https://npm.pkg.github.com
> Username: USERNAME
> Password: TOKEN
> Email: PUBLIC-EMAIL-ADDRESS
```

ビルドタスクを実行

```bash
$ ./gradlew buildFrontEnd
```

PostgreSQL を起動する。（dockerで起動する場合）

```bash
$ docker-compose up -d
```

Spring boot の Profile を `prod` に設定することにより PostgreSQL に接続される  

```bash
$ spring_profiles_active=prod ./gradlew bootRun
```
