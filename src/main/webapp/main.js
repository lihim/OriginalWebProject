
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
$(document).ready(function() {
    $.ajax({
        type:"GET",
        url: "ItemsInformationByCardTab",
        data:"Isracard",
        dataType: "json",
        success: function(data, textStatus, jqXHR){
            if(data.success){
                setItemTable(data);
            }
            else{
                $("#card-table").html("<div><b>item is invalid!</b></div>");
                $(this).removeClass('active');
            }
        }
    })
    $(this).addClass('active');

});


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
           url: "ItemsInformation",
           data: item,
           dataType: "json",
           success: function(data, textStatus, jqXHR){
               console.log("in success");
               if(data.success){
                 setItemTable(data);
                 var card = data.itemsInfo[0].card_name;
                   $("." + card).click();
               }
               else{
                   $("#card-table").html("<div><b>item is invalid!</b></div>");
               }
           },

           error: function(jqXHR, textStatus, errorThrown){
               console.log("Something really bad happened " + textStatus);
               $("#card-table").html(jqXHR.responseText);
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
    txt =   "<tr><th class = 'table-header date'>Date</th>" +
            "<th class = 'table-header store'>Store</th>" +
            "<th class = 'table-header transaction'>Transaction Amount</th>" +
            "<th class = 'table-header payments' >Number of Payments</th>" +
            "<th class = 'table-header debit'>Debit Amount</th>" +
            "<th class = 'table-header additional'>Additional Payments</th></tr>";
    $(".tablehead").html("");
    $(".tablehead").append(txt);
    txt = "";
    for(var i = 0 ; i < data.itemsInfo.length ; i++){
        txt += "<tr class = 'item-row'><td class ='item-cell date'>" + data.itemsInfo[i].purchase_date + "</td>";
        txt += "<td class = 'item-cell store'>" + data.itemsInfo[i].store + "</td>";
        txt += "<td class = 'item-cell transaction'>" + data.itemsInfo[i].transaction_amount + "</td>";
        txt += "<td class = 'item-cell payments'>" + data.itemsInfo[i].number_of_payments + "</td>";
        txt += "<td class = 'item-cell debit'>" + data.itemsInfo[i].debit_amount +"</td>";
        txt += "<td class = 'item-cell additional'>" + data.itemsInfo[i].additional_payments +"</td>";
        txt += "<td class = 'delete-cell'><a href='#'> <span class='glyphicon glyphicon-trash' ></span> </a></td></tr>";
   }
    $(".tablebody").html("");
    $(".tablebody").append(txt);



    return txt;

}


$( function() {
    $('.tab-panels .tabs li ').click('tabsselected', function(){
        $('.tab-panels .tabs li.active').removeClass('active');

        $.ajax({
            type:"GET",
            url: "ItemsInformationByCardTab",
            data:$(this).text(),
            dataType: "json",
            success: function(data, textStatus, jqXHR){
                if(data.success){
                    setItemTable(data);
                }
                else{
                    $("#card-table").html("<div><b>item is invalid!</b></div>");
                    $(this).removeClass('active');
                }
            }
        })
        $(this).addClass('active');
    });
} );

