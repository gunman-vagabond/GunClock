GunClock = require("./GunClock").GunClock

module.exports = (robot) ->
  robot.respond /.*(time|何時).*/i, (msg) ->
    gunclock = new GunClock(10, "\r")
    msg.send gunclock.getGunClock()
