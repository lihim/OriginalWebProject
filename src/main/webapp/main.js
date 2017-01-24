
function updateSubmit(){
    if ($('#purchase_date').val().length>0 &&$(' #itemCode').val().length>0 &&
        $('#transaction_amount').val().length >0 && $('#number_of_payments' ).val().length > 0) {
        $("#add-button").removeAttr('disabled');
    }
    else {
        $('#add-button').prop("disabled", true);
    }
};

//disabling submit button until all fields are entered
$(document).ready(function (){
    updateSubmit();
    $('#purchase_date, #itemCode, #transaction_amount, #number_of_payments').on('change',function(){
        updateSubmit();
    });
});

//get all the data from mysql and show it when page is loaded
/*$(document).ready(function() {
    $.get("OnLoad", function(data, status){
        var myObj = JSON.parse(data);
        var txt = setItemTable(myObj);
        $('#ajaxResponse').html(txt);

    });
});*/


$(document).ready(function(){

    $("#card-form").submit(function (e) {
        e.preventDefault();
    });

    $("#add-button").click(function(e){

       var item = {
           purchase_date : $("input#purchase_date").val(),
           itemCode : $("input#itemCode").val(),
           transaction_amount : $("input#transaction_amount").val(),
           number_of_payments : $("input#number_of_payments").val(),
           card_name : $("#card_name option:selected").text()
        }

       $.ajax({
           type: "POST",
           url: "ItemInformation",
           data: item,
           dataType: "json",
           success: function(data, textStatus, jqXHR){
               console.log("in success");
               if(data.success){

                   $("#ajaxResponse").html("");
                   $("#ajaxResponse").append( setItemTable(data) );
               }
               else{
                   $("#ajaxResponse").html("<div><b>item is invalid!</b></div>");
               }
           },

           error: function(jqXHR, textStatus, errorThrown){
               console.log("Something really bad happened " + textStatus);
               $("#ajaxResponse").html(jqXHR.responseText);
           },

           beforeSend: function (jqXHR, settings) {
               console.log("in before");
               settings.data /*+= "$dummyData = whatever"*/;
               $('#add-button').attr("disabled", true);
               console.log("end before Send");

           },

           complete: function(jqXHR, textStatus){
               //enable the button
               $('#myButton').attr("disabled", false);
           }

       })
    });
});

function setItemTable(data) {
    var txt = "";
    txt += "<table id = card-details-table  <!--class = scrollit-->";
    txt += "<thead class = tablehead><tr>" +
        "<th>Date</th>" +
        "<th>Store</th>" +
        "<th>Transaction Amount</th>" +
        "<th>Number of Payments</th>" +
        "<th>Debit Amount</th>" +
        "<th>Additional Payments</th>" +
        "</tr></thead>";
    txt += "<tbody class = tablebody >"
    for(var i = 0 ; i < data.itemsInfo.length ; i++){
        txt += "<tr><td>" + data.itemsInfo[i].purchase_date + "</td>";
        txt += "<td>" + data.itemsInfo[i].store + "</td>";
        txt += "<td>" + data.itemsInfo[i].transaction_amount + "</td>";
        txt += "<td>" + data.itemsInfo[i].number_of_payments + "</td>";
        txt += "<td >" + data.itemsInfo[i].debit_amount +"</td>";
        txt += "<td >" + data.itemsInfo[i].additional_payments +"</td></tr>";

    }
    txt += "</tbody></table>"
    return txt;

}

$(".tablinks").tabs({
    select: function(event, ui) {

        $.get("ItemInformation", function (data, status) {
            var myObj = JSON.parse(data);
            myObj.set("card_name", this.text);
            var txt = setItemTable(myObj);
            $('#ajaxResponse').html(txt);

        });
    }
});

/*
function tableOnClick(cardNAme){
    $.get("ItemInformation", function(data, status){
        var myObj = JSON.parse(data);
        myObj.set("card_name", cardNAme);
        var txt = setItemTable(myObj);
        $('#ajaxResponse').html(txt);

    });
}

$(function() {
    $("#tab").tabs({"select":function(event,ui){
        if (ui.index == 1) {
            $(".hide").switchClass('hide', 'show');
        } else {
            $(".show").switchClass("show", "hide");
        }
    }});
});
*/
$( function() {
    $('.tab-panels .tabs li ').click('tabsselected', function(){
        $('.tab-panels .tabs li.active').removeClass('active');

        $.ajax({
            type:"GET",
            url: "ItemInformation",
            data:$(this).text(),
            dataType: "json",
            success: function(data, textStatus, jqXHR){
                if(data.success){

                    $("#ajaxResponse").html("");
                    $("#ajaxResponse").append( setItemTable(data) );
                }
                else{
                    $("#ajaxResponse").html("<div><b>item is invalid!</b></div>");
                    $(this).removeClass('active');
                }
            }
        })
        $(this).addClass('active');
    });
} );


/*function calculateDebitAmount(transactionAmount, numberOfPayments){
    return (transactionAmount/numberOfPayments).toFixed(3);
}

function calculateAdditionalPayments(date, numberOfPayments){
    var transactionDate = new Date(date);
    var transactionMonth = transactionDate.getMonth();
    var today = new Date();
    var todayMonth = today.getMonth();

    return numberOfPayments - Math.abs(todayMonth - transactionMonth);
}*/

// $(function () {
//     var $orders = $('#orders');
//     $.ajax({
//         type: 'GET',
//         url:'orders',
//         success:function (orders) {
//             $.each(orders, function (i, order) {
//                 $orders.append('<li>my orders</li>')
//             })
//         }
//     });
// });
