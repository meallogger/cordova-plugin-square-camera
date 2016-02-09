var exec = require('cordova/exec')

function extend(o1, o2) {
  for (var i in o2) {
    if (o2.hasOwnProperty(i)) {
      o1[i] = o2[i];
    }
  }
  return o1
}

exports.getPicture = function(success, error, opts) {
  opts = extend({
    pictureMinWidth: null,
    storageDirectory: "SquareCamera"
  }, opts || {})

  exec(success, error, "SquareCamera", "getPicture", [opts]);
}
