# 概要
- JAX-RS (Jersey) でRESTインタフェースを実現しました。
- type=html/text/json(2種類)/jsonp のパターンを試しました。

# usage

## type=html

- /GunmanREST/rest/gunclock/html/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/html/20

```
<html> <title>GunClock-REST(html)</title><body><pre>                   12                   
          +                   +         
                                        
                                        
                                        
  +                                 +   
                                        
                                        
                              __        
        _________           _|__|_      
9       | 03:17 |  ::::::::b (@@) |__P3 
        ~~~~~~~~~           V|~~|>   l  
                             //T| ~l_l  
                                        
  +                                 +   
                                        
                                        
                                        
          +                   +         
                    6                   
</body></pre></html> 
```

## type=text

- /GunmanREST/rest/gunclock/text/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/text/20

```
                   12                   
          +                   +         
                                        
                                        
                                        
  +                                 +   
                                        
                              __AA      
                              __  |__P  
        _________           _|__|_   l  
9       | 03:13 |  ::::::::b (@@) ~l_l3 
        ~~~~~~~~~           V|~~|>      
                             //T|       
                                        
  +                                 +   
                                        
                                        
                                        
          +                   +         
                    6                   
```

## type=json

- /GunmanREST/rest/gunclock/json/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/json/20

## type=json(2)

- /GunmanREST/rest/gunclock/json2/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/json2/20

## type=jsonp

- /GunmanREST/rest/gunclock/jsonp/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/jsonp/20
