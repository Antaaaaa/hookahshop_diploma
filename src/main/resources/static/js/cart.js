$(document).ready(function () {
    loadFromDb();
    $(".item-add").click(function (event) {
        event.preventDefault();
        var id = $(this).attr("data-id");
        var name = $(this).attr("data-name");
        var price = Number($(this).attr("data-price"));
        var img = $(this).attr("data-img");
        addItemToCart(id,name, price, img, 1);
    });

    $("#show-cart").on("click", ".delete-item", function (event) {
        var id = $(this).attr("data-id");
        removeItemFromCartAll(id);
    });
    $("#show-cart").on("click", ".minus", function (event) {
        var id = $(this).attr("data-id");
        removeItemFromCart(id);
    });
    $("#show-cart").on("click", ".plus", function (event) {
        var id = $(this).attr('data-id');
        addItemToCart(id,"", 0, "", 1);
    });
    $(document).on('click', '.btn-group .dropdown-toggle', function (e) {
        e.stopPropagation();
    });

    function animationClick(trigger, element, animation){
        element = $(element);
        trigger = $(trigger);
        trigger.click(
            function() {
                element.addClass('animated ' + animation);
                //wait for animation to finish before removing classes
                window.setTimeout( function(){
                    element.removeClass('animated ' + animation);
                }, 500);

            });
    }
    $(".item-add").click(function (event) {
        element = $(this);
        element.addClass('animated pulse');
        window.setTimeout( function(){
            element.removeClass('animated pulse');
        }, 500);
    })
});
// let http_url = "http://localhost:8080/";
let http_url = "http://hookah-shop.herokuapp.com/";
function loadFromDb(){
    $.getJSON(http_url + 'cart/', function(data) {
        // deleteCookie();
        for (var i = 0; i < data.selectedItems.length; i++){
            var item = new Item(data.selectedItems[i].shopProductDTO.id,
                data.selectedItems[i].shopProductDTO.name,
                data.selectedItems[i].shopProductDTO.price,
                data.selectedItems[i].shopProductDTO.imageUrl,
                data.selectedItems[i].number);
            cart.push(item);
            /* saveCart(); */
            displayCart();
        }
    });
}
function displayCart(){
    var list = listCart();
    if (list.length === 0) return;
    var output = "";
    for (var i = 0; i < list.length; i++){
        output += "<div class='row mb-2 mt-2 p-2'><div class='col-md-4 col-sm-4 col-xs-4'>"+
            "<a href='/shop/" + list[i].id + "'>" +
            "<img src='"+ list[i].img +"' class='mx-auto d-block' width='80' height='80'></a></div>"+
            "<div class='col-md-8 col-sm-8 col-xs-8 text-center m-auto'><span class='name'>"+list[i].name+"</span>"+
            "<br><button class='btn minus mr-2'  style='min-width: 0 !important;' data-id='"+ list[i].id +"'>" +
            "<i class='fas fa-minus-circle'></i></button><span>"+list[i].count+"</span>"+
            "<button class='btn plus ml-2'  style='min-width: 0 !important;' data-id='"+ list[i].id +"'><i class='fas fa-plus-circle'></i>" +
            "</button><button class='btn delete-item m-1'  style='min-width: 0 !important;' data-id='"+ list[i].id +"'><i class='fas fa-times-circle'></i></button>" +
            "</div></div><legend></legend>";
    }
    var footer = "<span class='m-2 w-100'>Загальна:"+totalCart()+" грн</span>"+
        "<a href='/order' class='btn btn-primary m-2 to-order'>Зробити замовлення</a>";
    $("#show-cart").html(output);
    $("#total-cart").html(footer);
    $("#count").html(totalCart()+" грн");
}


var cart = [];
var Item = function (id, name, price, img, count) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.count = count;
    this.img = img;
};


// function clear() {
//     cart = [];
// };


function addItemToCart(id, name, price, img, count) {
    $.ajax({
        url : http_url + 'cart/add/'+id,
        type : 'POST',
        statusCode : {
            200: function() {
                for(var i in cart) {
                    if (cart[i].id == id) {
                        cart[i].count += count;
                        // saveCart();
                        displayCart();
                        return;
                    }
                }
                var item = new Item(id, name, price, img, count);
                cart.push(item);
                // saveCart();
                displayCart();
                return;
            },
            // user not logged in
            401: function () {
                window.location.href = "/auth";
            },
            400: function () {
                if ($('#note').hasClass("fadeout"))
                        $("#note").removeClass("fadeout").addClass("fadein");
                window.setTimeout( function(){
                    $("#note").removeClass("fadein").addClass("fadeout");
                }, 5000);
            }
        }
    });
};

function removeItemFromCart(id) {
    $.ajax({
        url : http_url + 'cart/minus/'+id,
        type : 'POST',
        statusCode : {
            200: function () {
                for (var i in cart) {
                    if (cart[i].id == id) {
                        cart[i].count--;
                        if (cart[i].count === 0)
                            cart.splice(i, 1);
                    }
                }
                // saveCart();
                displayCart();
                if (cart.length == 0) {
                    $('#total-cart').html("");
                    $('#show-cart').html("<h6 class='text-center'>Ваш кошик порожній &#x1f61f;</h6>");
                    $('#count').html("");
                }
                return;
            },
            // user not logged in
            401: function () {
                window.location.href = "/auth";
            },
            400: function () {
                if ($('#note').hasClass("fadeout"))
                    $("#note").removeClass("fadeout").addClass("fadein");
                window.setTimeout( function(){
                    $("#note").removeClass("fadein").addClass("fadeout");
                }, 5000);
            }
        }
    });
};

function removeItemFromCartAll(id) {
    $.ajax({
        url : http_url + 'cart/minusall/'+id,
        type : 'POST',
        statusCode: {
            200: function() {
                for (var i in cart) {
                    if (cart[i].id == id) {
                        cart.splice(i, 1);
                        break;
                    }
                }
                // saveCart();
                displayCart();
                if (cart.length == 0) {
                    $('#total-cart').html("");
                    $('#show-cart').html("<h6 class='text-center'>Ваш кошик порожній &#x1f61f;</h6>");
                    $('#count').html("");
                }
                return;
            },
            // user not logged in
            401: function () {
                window.location.href = "/auth";
            },
            400: function () {
                if ($('#note').hasClass("fadeout"))
                    $("#note").removeClass("fadeout").addClass("fadein");
                window.setTimeout( function(){
                    $("#note").removeClass("fadein").addClass("fadeout");
                }, 5000);
            }
        }
    });
};
;

function totalCart() {
    var totalCost = 0;
    for (var i in cart) {
        totalCost += cart[i].price * cart[i].count;
    }
    return totalCost.toFixed(2);
};

function listCart() {
    var cartCopy = [];
    for (var i in cart) {
        var item = cart[i];
        var itemCopy = {};
        for (var p in item) {
            itemCopy[p] = item[p];
        }
        itemCopy.total = item.price * item.count;
        cartCopy.push(itemCopy);
    }
    return cartCopy;
};

// function saveCart() {
//     var myObject = JSON.stringify(cart);
//     document.cookie = 'cart=' + myObject + ';expires=' + new Date(new Date().getTime()+60*60*1000*24).toGMTString();
// };
// function deleteCookie( ) {
//     document.cookie = 'cart' + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
// }
