var app = angular.module('plunker', ['ui.bootstrap', 'ngFileUpload','ngFlash']);
app.config(['FlashProvider',
  function( FlashProvider) {
    FlashProvider.setTimeout(5000);
    
}])


app.controller('MainCtrl', function($scope,ApplicationService,Flash) {
  $scope.name = 'World';
  $scope.fileArr = [];
  $scope.key =""
  $scope.value = ""
  $scope.similarPatterns = {}
  $scope.res = [];
 
console.log($scope.res);
  console.log($scope.value)
  $scope.$on('uploadfile',function(event,data)
  {
       if($scope.fileArr.length<2)
           {
               $scope.fileArr.push(data.name)
             }
  })
 console.log(name)

 $scope.onSubmit = function()
 {
   if($scope.fileArr.length<2)
   {
     var message = 'Please upload another the files '
     Flash.create('danger', message, 5000, true);
   }
   else
   {
     var message = 'Please Wait while we process the request '
     Flash.create('success', message, 7000, true);
    console.log($scope.fileArr)
    var handleSuccess = function(response)
    {
     console.log("in h s")
     console.log(response)
     $scope.similarPatterns = response.data;
      for (key in $scope.similarPatterns)
      {
         $scope.res.push({'value' : key , 'name' : $scope.similarPatterns[key] })
      }
      
    }
    var handleFailure = function(error)
    {
     var message = 'Error while processing the request :('
     Flash.create('error', message, 5000, true);
    }
   ApplicationService.getResults($scope.fileArr[0],$scope.fileArr[1]).then(handleSuccess,handleFailure)
     
   }
   console.log("on submit");
   
 }


$scope.onSave = function()
 {
   console.log("on save")
    var obj = { }
    var arr = []
    console.log($scope.value)
    if($scope.value!="" &&$scope.key!="")
    {
      //arr.push($scope.key)
      arr.push($scope.value)
      obj[$scope.key] = arr
      console.log(obj)
      
      var success = function(response)
      {
        var message = 'Added to dictionary successfully!'
        Flash.create('success', message, 5000, true);
      }
      var error = function(error)
      {
        var message = 'Error while adding to dictionary '
        Flash.create('danger', message, 5000, true);
      }
      ApplicationService.setPatterns(obj).then(success,failure);
    }
    else
    {
      var message = 'Please enter both key and value '
      Flash.create('danger', message, 5000, true);
    }
   
 }

});


app.service('ApplicationService', ['$http',ApplicationService]);
function ApplicationService($http)
{
  function getResults(path,path1)
        {
        	var response = "";
        	var url = "http://13.126.15.114:8080/RS/Results/?filepath1="+path+"&filepath2="+path1;
    
		 	  return  $http({
    url: url,
    dataType: 'json',
    method: 'GET',
    headers: {
        "Content-Type": "application/json"
    }})
        };

    function setPatterns(obj)
    {
      var response = "";
      var url = "http://13.126.15.114:8080/RS/setPatterns/"

      return $http.post(url,obj,"")
    }

  return{
    getResults:getResults,
    setPatterns:setPatterns
  }
}



app.directive('uploadFile',[function()
{
  return{
    templateUrl:"uploadFile.html",
    scope:{fileArr:"="},
    controller:['$scope','Upload','Flash',function($scope,Upload,Flash)
    {
      $scope.fileArr = [];
      console.log("in uploadFile controller")
       $scope.uploadFiles = function(file,errfiles)
       {
          if (file) {
            console.log(file.name)
          
           
            file.upload = Upload.upload({
                url: 'http://13.126.15.114:8080/RS/upload',
                data: {'uploadfile': file}
            });

            file.upload.then(function (response) {
              //  $timeout(function () {
                    file.result = response.data;
                     $scope.$emit('uploadfile',{'name':file.name})
                      var message = 'File uploaded successfully!'
                      Flash.create('success', message, 5000, true);
                    console.log(file.result)
                   
                //});
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 * 
                                         evt.loaded / evt.total));
            });
        }   
    
    
    //return file.result;
         
       }
    }]
  }
}]);
