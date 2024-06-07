function getCourseTypes() {
    $.ajax({
        type: 'GET',
        url: '/courseType/getAll',
        success: function (result) {
            displayCourseTypeResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayCourseTypeResults(results) {
    var Results = document.getElementById('courseTypeResult');
    if(results === ""){
        Results.innerHTML="<li> Chưa có loại khóa học nào!</li>";
    }
    else{
        Results.innerHTML = "";

        var parsedResults = $.parseHTML(results);

        $(Results).append(parsedResults);
    }
}

$(document).ready(function () {
    getCourseTypes();
});