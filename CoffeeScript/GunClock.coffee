class GunClock

  strArrayGunClock = []

  class Cast
    constructor: (strArrayImage) ->
      @gunclock = strArrayGunClock
      @image = strArrayImage
      @gunclocksize = strArrayGunClock.length

    display: (x, y) ->
      _x = Math.ceil(x*2 - (@image[0].length /2))
      _y = Math.ceil(y   - @image.length / 2)

      for i in [0 ... @image.length]
        if ( _y+i < 0 || _y+i >= @gunclocksize ) 
          continue

        strBufTmp = "";
        if ( _x > 0 )
          strBufTmp = @gunclock[_y+i].substr(0,_x)

        for j in [0 ... @image[i].length ]

          if ( _x+j >= 0 && _x+j <= @gunclocksize*2)
            if( (c = @image[i].charAt(j)) != '*' )
              strBufTmp = strBufTmp + @image[i].charAt(j);
            else
              strBufTmp += @gunclock[_y+i].charAt(_x+j);

        if ( _x+@image[i].length <= @gunclocksize*2 )
          strBufTmp += @gunclock[_y+i].substring(_x+@image[i].length, @gunclocksize*2 );

        @gunclock[_y+i] = strBufTmp;



  strNewline = ""
#  strNewline = "\n"
#  strNewline = "\r"

  constructor: (gunclocksize, _strNewline) ->
    @gunclocksize = gunclocksize
    strNewline = _strNewline
    @initGunClock(@gunclocksize)

    @gunman = new Cast([
      "*  __ *", 
      " _|__|_",
      "b (@@) ",
      " V|~~|>",
      "  //T| "
    ])

    @inu = new Cast([
      "__AA  * ",
      "| 6 |__P",
      "~~|    l",
      " /_/~l_l"
    ])

    @longHand  = new Cast( [ "##" ] )
    @shortHand = new Cast( [ "::" ] )

    @num3      = new Cast( [ "3" ] )
    @num6      = new Cast( [ "6" ] )
    @num9      = new Cast( [ "9" ] )
    @num12     = new Cast( [ "12"] )
    @waku      = new Cast( [ "+" ] )

    @makeGunClock(@gunclocksize)

  initGunClock : (gunclocksize) ->
    strArrayGunClock = []
    for i in [0 ... gunclocksize]
      str = "";
      for j in [0 ... gunclocksize*2]
        str += " "

      strArrayGunClock.push(str)


  makeGunClock : (gunclocksize) ->

    d = new Date()
    hour = d.getHours()
    minute = d.getMinutes()
    second = d.getSeconds()

    centerX = Math.ceil(gunclocksize/2)
    centerY = Math.ceil(gunclocksize/2)

    gunmanX = centerX + Math.cos(hourToRadian(hour,minute)) * (gunclocksize*2/3/2)
    gunmanY = centerY - Math.sin(hourToRadian(hour,minute)) * (gunclocksize*2/3/2)

    inuX = centerX + Math.cos(minuteToRadian(minute,second)) * (gunclocksize*4/5/2) 
    inuY = centerY - Math.sin(minuteToRadian(minute,second)) * (gunclocksize*4/5/2) 

    for i in [0 ... 360] by 30
      if ( i==0 || i==90 || i==180 || i==270 )
        continue
      radian = (i * 2 * Math.PI) / 360

      wakuXdiff = gunclocksize/2 * Math.cos(radian)
      wakuYdiff = gunclocksize/2 * Math.sin(radian)

      if ( wakuXdiff >=0 )
        wakuX = centerX + wakuXdiff
      else
        wakuX = centerX + wakuXdiff

      if ( wakuYdiff >=0 )
        wakuY = centerY + wakuYdiff
      else
        wakuY = centerY + wakuYdiff

      @waku.display(wakuX, wakuY)

    tmp = gunclocksize
    tmp--
#    alert("tmp="+tmp)

    @num3.display(tmp , centerY )
    @num6.display(centerX , tmp )
    @num9.display(0 , centerY )
    @num12.display(centerX , 0)

    for i in [0 ... gunclocksize*2/3/2]

      longHandX = centerX + ( ((inuX - centerX) * i) / (gunclocksize*2/3/2) )
      longHandY = centerY + ( ((inuY - centerY) * i) / (gunclocksize*2/3/2) )

      @longHand.display(longHandX, longHandY)

    for i in [0 ... gunclocksize*5/6/2]
      shortHandX = centerX + ( ((gunmanX - centerX) * i) / (gunclocksize*5/6/2) )
      shortHandY = centerY + ( ((gunmanY - centerY) * i) / (gunclocksize*5/6/2) )

      @shortHand.display(shortHandX, shortHandY)

    @inu.display(inuX, inuY)
    @gunman.display(gunmanX, gunmanY)

    strBufTime = ""

    if(hour<10)
      strBufTime += "0"
    strBufTime += hour+":"
    if(minute<10)
      strBufTime += "0"
    strBufTime += minute+":"
    if(second<10)
      strBufTime += "0"
    strBufTime += second

    digital = new Cast([
      "____________",
      "| " + strBufTime + " |",
      "~~~~~~~~~~~~"
    ])

    digitalRad = digitalRadian(hour,minute,second)


    digital.display(centerX + parseInt(Math.cos(digitalRad) * gunclocksize/2 *1/2)
          , centerY - parseInt(Math.sin(digitalRad) * gunclocksize/2 *1/2)
    )


  toString = (strArray) ->
    ret = ""
    for str, i in strArray
      ret += str
      ret += strNewline
    ret


  hourToRadian = (h, m) ->
    Math.PI * (90.0 - ((h%12) + m/60.0) * 30.0) / 180.0

  minuteToRadian = (m, s) ->
    Math.PI * (90.0 - (m + s/60.0) * 6.0) / 180.0

  digitalRadian = (h, m, s) ->
    hRadian = hourToRadian(h,m)
    mRadian = minuteToRadian(m,s)

    aveRadian = (hRadian + mRadian) / 2


    if ( ((hRadian > mRadian) && ( (hRadian - mRadian) < Math.PI)) || ((mRadian > hRadian) && ( (mRadian - hRadian) < Math.PI)) )
      aveRadian + Math.PI
    else
      aveRadian

  getGunClock: -> 
    toString(strArrayGunClock)

exports.GunClock = GunClock
