function getVideo(id, index) {

    $.ajax({
        type: 'GET',
        url: '/api/course/getVideo',
        data: { id: id, index:index },
        success: function (result) {
            displayResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayResults(results) {
    let Results = document.getElementById('display-video');
    Results.innerHTML = `<iframe src="${results}" title="YouTube video player" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen>`;
}