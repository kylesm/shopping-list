var ShoppingList = ShoppingList || {};

jQuery(document).ready(function($) {
	var sid = 1;
	var listItemTemplate = Handlebars.templates['listItem.tmpl'];

	$.ajaxSetup({
		contentType: 'application/json',
		processData: false
	});

	$.ajaxPrefilter(function (options, originalOptions, xhr) {
		if (options.data) {
			options.data = JSON.stringify(options.data);
		}
	});

	$("#checkOffAllLink").click(function () {
		console.log("check all");
		$("input[type='checkbox']").attr("checked", true).trigger("change").checkboxradio("refresh");
	});

	$("#saveItemButton").click(function () {
		var addName = $("#add-name");
		var addQuantity = $("#add-quantity");

		var item = {
			name: addName.val(),
			quantity: addQuantity.val(),
			obtained: false
		};

		addName.val("");
		addQuantity.val(1).slider("refresh");

		$.ajax("shoppingList/" + sid + "/listItem", {
			type: "POST",
			data: item,
			success: function (data) {
				var html = listItemTemplate(data);
				$("#shoppingList").append(html).trigger('create');

				console.log("Item added");
			},
			error: function (xhr, status, error) {
				console.log("ERROR: " + error);
			}
		});
	});

	$.ajax("shoppingList/" + sid, {
		success: function (data) {
			$('#listName').text(data.description);

			data.items.forEach(function (item) {
				var html = listItemTemplate(item);
				$("#shoppingList").append(html).trigger('create');
			});

			$("input[type='checkbox']").on("change", function (event) {
				var data = {
					obtained: $(this).is(':checked')
				};

				$.ajax('shoppingList/' + sid + '/listItem/' + $(this).attr('value'), {
					type: 'PUT',
					data: data,
					success: function (data, status, xhr) {
						console.log('Updated list item');
					},
					error: function (xhr, status, error) {
						console.log("ERROR: " + error + " (" + status + ")");
					}
				});
			});
		},
		error: function (xhr, status, error) {
			console.log("ERROR: " + error);
		}
	});
});
