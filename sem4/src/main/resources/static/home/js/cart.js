function getCart() {
    $.ajax({
        type: 'GET',
        url: '/cart/getAll',
        success: function (result) {
            displayCartResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayCartResults(results) {
    var Results = document.getElementById('cartResult');
    if(results === ""){
        Results.innerHTML="<li class=\"cart-2-li\"> Chưa có sản phẩm nào trong giỏ hàng! </li>";
    }
    else{
        Results.innerHTML = "";

        var parsedResults = $.parseHTML(results);

        $(Results).append(parsedResults);
    }
}


function getCountCart() {
    $.ajax({
        type: 'GET',
        url: '/api/cart/getCount',
        success: function (result) {
            displayCountCartResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayCountCartResults(results) {
    var Results = document.getElementById('countCart');
    Results.innerHTML = results;
}

$(document).ready(function () {
    getCart();
    getCountCart();
});

function addToCart(id) {

    $.ajax({
        type: 'Get',
        url: '/api/cart/addToCart/'+id,
        success: function (result) {
            console.log(result);
            getCart();
            getCountCart();
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

$("#button-add-to-cart").on("click", function () {
    let courseId = document.getElementById("hidden-course-id").value;
    addToCart(courseId);
});

