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

```

{"message":"                       12                         ,                                                  ,            +                       +             ,                                                  ,                                                  ,                      _________                   ,    +                 | 03:45 |             +     ,                      ~~~~~~~~~                   ,                                                  ,                                                  ,  __AA                                            ,  | 6 |__P                                        ,9 ~~|    l#############::::::                   3 ,   /_/~l_l                 ::::::     __          ,                               :::: _|__|_        ,                                   b (@@)         ,                                    V|~~|>        ,    +                                //T|   +     ,                                                  ,                                                  ,                                                  ,                                                  ,            +                       +             ,                                                  ,                        6                         "}
```

## type=json(2)

- /GunmanREST/rest/gunclock/json2/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/json2/20

```
["                       12                         ","                                                  ","            +                       +             ","                                                  ","                                                  ","                                                  ","    +                 _________             +     ","                      | 03:47 |                   ","  __AA                ~~~~~~~~~                   ","  | 6 |__P                                        ","  ~~|    l                                        ","   /_/~l_l#######                                 ","9                ######::::::                   3 ","                           ::::::     __          ","                               :::: _|__|_        ","                                   b (@@)         ","                                    V|~~|>        ","    +                                //T|   +     ","                                                  ","                                                  ","                                                  ","                                                  ","            +                       +             ","                                                  ","                        6                         "]
```

## type=jsonp

- /GunmanREST/rest/gunclock/jsonp/<時計のサイズ>
  - ex.) http://xxx.xxx:8080/GunmanREST/rest/gunclock/jsonp/20

```
callback({"message":"                       12                         ,                                                  ,            +                       +             ,                                                  ,                                                  ,                                                  ,    +                   _________           +     ,                        | 03:47 |                 ,  __AA                  ~~~~~~~~~                 ,  | 6 |__P                                        ,  ~~|    l                                        ,   /_/~l_l#######                                 ,9                ######::::::                   3 ,                           ::::::     __          ,                               :::: _|__|_        ,                                   b (@@)         ,                                    V|~~|>        ,    +                                //T|   +     ,                                                  ,                                                  ,                                                  ,                                                  ,            +                       +             ,                                                  ,                        6                         "})
```
