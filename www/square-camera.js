var exec = require('cordova/exec')

exports.getPicture = function(success, error) {
  exec(success, error, "SquareCamera", "getPicture", []);
}
