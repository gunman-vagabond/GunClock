# maven GunClock

GunClock for maven.


## 実行方法
    mvn package

    → target/gunman.war が生成されます

## memo

■プロジェクトの生成(mvnで)

  % mvn archetype:generate -DgroupId=com.gunman -DartifactId=web-app -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false


■pom.xml

-------------------------------
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.gunman</groupId>
  <artifactId>web-app</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>web-app Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
 
 ★<dependency>を追加。versionは3.1.0にしてみた。web.xml書かないでやってみる。
 ★scopeはprovidedにすることで、warに入らない(サーバのを使う)
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
 
  </dependencies>
  <build>
 
 ★Nameを買える。warのファイル名になった。
    <finalName>gunclock</finalName>
 
 ★pluginsを追加。versionは3.1.0にしてみる。web.xml書かないでやってみる。
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.1.0</version>
      </plugin>
    </plugins>

  </build>
 </project>

■web.xml
- servlet 3.0 の web.xml にする。
- Servletに関する記述は、一切しないでOK。
-------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 
 <web-app xmlns="http://java.sun.com/xml/ns/javaee"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
	      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	      version="3.0">
 </web-app>
-------------------------


■アクセス
 http://xxxx:8080/gunclock/gunclock

- 一つ目のgunclockはwarファイル名
- ２つ目のgunclockはアノテーションで書いた名前


