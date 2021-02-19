# csvOutputter
## 前提事項
* mavenが使用できること
* JDKのbinのパスが通っていること

**※以下のバージョンで動作確認しました**
```
#java -version
openjdk version "15.0.1" 2020-10-20
OpenJDK Runtime Environment (build 15.0.1+9-18)
OpenJDK 64-Bit Server VM (build 15.0.1+9-18, mixed mode, sharing)

#javac -version
javac 15.0.1

#mvn -version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: C:\apache-maven-3.6.3\bin\..
Java version: 15.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\openjdk-15.0.1_windows-x64_bin\jdk-15.0.1
Default locale: ja_JP, platform encoding: MS932
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## ビルド・起動方法
1. pom.xml直下で以下のコマンド実行
   ```
   mvn install
   ```

2. ビルド後、`target`内の`csvOutputter-0.0.1-SNAPSHOT-jar-with-dependencies.jar`を、`start.bat`のある階層にコピー

3. `start.bat`を実行

### ※起動時の注意事項
* `csvOutputter.properties`が同一階層に存在すること
* パスに通っているJavaのバージョンが古いバージョンだと起動できない（1.8だと動作しないことを確認）
