var transform = function(data) {
	//console.log(data)
	return $.param(data);
}
function angularPost(url, data, $http, callback) {
	$http.post(url, data, {
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
		},
		transformRequest : transform
	}).success(function(data) {
		callback(data);
	});
}