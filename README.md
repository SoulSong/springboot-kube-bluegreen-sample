# Background
This is a simple example of a blue-green release. It depends on springboot2, builds by maven, deploys by kubernetes.

# How To Use

## Change Version
Change the project version in `pom.xml` as follow:
```pom
    <groupId>com.shf.example</groupId>
    <artifactId>springboot-kube-sample</artifactId>
    <version>Blue</version>
```    
It will be read in `VersionController`:
```java
@Slf4j
@RestController
public class VersionController {

    @Autowired(required = false)
    private BuildProperties buildProperties;

    @GetMapping("/version")
    public String version() {
        if (null == buildProperties) {
            return "UnKnow";
        }
        return buildProperties.getVersion();
    }

}
```

## Build image 
Dockerfile is in ./docker folder, just input `mvn clean install` command. Then we could get two images:
* local-dtr.com/springboot-kube-sample:Blue                   
* local-dtr.com/springboot-kube-sample:Green

## Deploy into kubernetes cluster
In ./kubernetes folder, you will find all yaml-files for kubernetes.

### Create namespace
* kubectl create namespace blue-green

### Create deployment
* kubectl apply -f deployment-blue.yaml
* kubectl apply -f deployment-green.yaml

### Create service(`blue`)
* kubectl apply -f service-blue.yaml

### Create ingress
* kubectl apply -f ingress.yaml -n blue-green

### Check Version
> **INPUT**
```
curl -i http://shf.sample.com:8080/sample-service/version
```
> **OUTPUT**
```
curl -i http://shf.sample.com/sample-service/version
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100     4  100     4    0     0    129      0 --:--:-- --:--:-- --:--:--   129HTTP/1.1 200
Server: nginx/1.15.10
Date: Tue, 23 Apr 2019 07:51:52 GMT
Content-Type: text/plain;charset=UTF-8
Content-Length: 4
Connection: keep-alive

Blue
```

### Change service verson(`green`)
* kubectl apply -f service-green.yaml

### ReCheck Version
> **INPUT**
```
curl -i http://shf.sample.com:8080/sample-service/version
```
> **OUTPUT**
```
curl -i http://shf.sample.com/sample-service/version
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100     5  100     5    0     0     14      0 --:--:-- --:--:-- --:--:--    14HTTP/1.1 200
Server: nginx/1.15.10
Date: Tue, 23 Apr 2019 08:01:13 GMT
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Connection: keep-alive

Green
```
