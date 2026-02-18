const baseUrl = "http://localhost:8080/api/v1/customer";

$(document).ready(function () {
    getAllCustomer()
})

$("#btn-save").on("click", function () {
    saveCustomer();
})
$("#btn-update").on("click", function () {
    updateCustomer();
})
$("#btn-delete").on("click", function () {
    deleteCustomer();
})

function saveCustomer() {
    // const id=$('#id').val()
    const name=$('#name').val()
    const address=$('#address').val()
    $.ajax({
        url:baseUrl,
        method:'POST',
        contentType:'application/json',
        data:JSON.stringify({
            // "id":id,
            "name":name,
            "address":address
        }),
        success:function (response) {
            console.log(response.status)
            console.log(response.message)
            getAllCustomer()
            clearCustomer()
            alert(response.message)
        },
        error: function(error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            const stts = error.responseJSON?.status
            console.log(stts);
            console.log(msg);
            alert(msg);
        }
    })
}
function updateCustomer(){
    const id=$('#id').val()
    const name=$('#name').val()
    const address=$('#address').val()
    $.ajax({
        url:baseUrl,
        method:'PUT',
        contentType:'application/json',
        data:JSON.stringify({
            "id":id,
            "name":name,
            "address":address
        }),
        success:function (response) {
            console.log(response.status)
            console.log(response.message)
            getAllCustomer()
            clearCustomer()
            alert(response.message)
        },
        error: function(error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            const stts = error.responseJSON?.status
            console.log(stts);
            console.log(msg);
            alert(msg);
        }
    })
}
function deleteCustomer() {
    const id=$('#id').val()
    $.ajax({
        url:baseUrl + '/'+id,
        method:'DELETE',
        success:function (response) {
            console.log(response.status)
            console.log(response.message)
            getAllCustomer()
            clearCustomer()
            alert(response.message)
        },
        error: function(error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            const stts = error.responseJSON?.status
            console.log(stts);
            console.log(msg);
            alert(msg);
        }
    })
}
function getAllCustomer() {
    $('#customerTableBody').empty()

    $.ajax({
        url: baseUrl,
        method: 'GET',
        success: function (response) {
            for (let customer of response.data) {
                const row =
                    "<tr>" +
                    "<td>" + customer.id + "</td>" +
                    "<td>" + customer.name + "</td>" +
                    "<td>" + customer.address + "</td>" +
                    "</tr>";

                $('#customerTableBody').append(row)
            }

            console.log(response.status)
            console.log(response.message)
        },
        error: function (error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            alert(msg);
        }
    })
}


function clearCustomer(){
    $('#id').val('')
    $('#name').val('')
    $('#address').val('')
}