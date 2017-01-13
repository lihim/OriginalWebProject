$(document).ready(function(){
    console.log("test")

    $("#card-form").submit(function (e) {
        e.preventDefault();
    });

    $("#add-button").click(function(e){
       dataString = $("#card-form").serialize();

       var itemCode = $("input#itemCode").val();
       //var price = $("#input#price").val();
       dataString = "itemCode=" + itemCode;

       $.ajax({
           type: "POST",
           url: "ItemInformation",
           data: dataString,
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

    function setItemTable(data) {
        var txt = "";
        txt += "<table border='1' class = scrollit>";
        txt += "<thead class='tablehead'><tr><th>Item</th></tr></thead>";
        txt += "<tbody class='tablebody'>"
        for(var i = 0 ; i < data.itemsInfo.length ; i++){
            txt += "<tr><td>" + data.itemsInfo[i] + "</td></tr>";
        }
        txt += "</tbody></table>"
        return txt;

    }


});




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
