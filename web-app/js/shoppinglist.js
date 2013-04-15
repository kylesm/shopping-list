/*
 * Copyright 2013 Kyle M. Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var ShoppingList = ShoppingList || {};

ShoppingList.currentListId = -1;

jQuery(document).ready(function($) {
	var listItemTemplate = Handlebars.templates['listItem.tmpl'];
	var listTemplate = Handlebars.templates['list.tmpl'];
	var shoppingLists = $("#shoppingLists");
	var shoppingList = $("#shoppingList");

	$.ajaxSetup({
		contentType: 'application/json',
		processData: false
	});

	$.ajaxPrefilter(function (options, originalOptions, xhr) {
		if (options.data) {
			options.data = JSON.stringify(options.data);
		}
	});

	$("div[data-role='popup']").on("popupafteropen", function () {
		$($(".ui-popup-active form :input:visible")[0]).focus();
	});

	var handleListLinkClick = function (event) {
		ShoppingList.currentListId = $(this).data('sid');

		$.ajax("shoppingList/" + ShoppingList.currentListId, {
			success: function (data) {
				shoppingList.empty();
				$("#listName").text(data.description);

				data.items.forEach(function (item) {
					var html = listItemTemplate(item);
					shoppingList.append(html).trigger('create');
				});

				$("input[type='checkbox']").on("change", function (event) {
					var data = {
						obtained: $(this).is(':checked')
					};

					$.ajax('shoppingList/' + ShoppingList.currentListId + '/listItem/' + $(this).attr('value'), {
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
	};

	$.ajax("shoppingList", {
		success: function (data) {
			shoppingLists.empty();
			data.forEach(function (item) {
				shoppingLists.append(listTemplate(item));
			});

			shoppingLists.listview("refresh");
		},
		error: function (xhr, status, error) {
			console.log("ERROR: " + error);
		}
	});

	shoppingLists.on("click", ".gotoList", handleListLinkClick);

	$("#checkOffAllLink").click(function () {
		console.log("check all");
		$("input[type='checkbox']").attr("checked", true).trigger("change").checkboxradio("refresh");
	});

	$("#homeButtonLink").click(function () {
		ShoppingList.currentListId = -1;
	});

	$("#saveListButton").click(function () {
		var newListName = $("#add-list-description");

		var list = {
			description: newListName.val()
		};

		newListName.val("");

		$.ajax("shoppingList", {
			type: "POST",
			data: list,
			success: function (data) {
				var html = listTemplate(data);
				shoppingLists.append(html);
				shoppingLists.listview("refresh");
			},
			error: function (xhr, status, error) {
				console.log("ERROR: " + error);
			}
		});
	});

	$("#saveItemsButton").click(function () {
		var items = $("#add-items");

		var data = {
			items: items.val()
		};

		items.val("");

		$.ajax("shoppingList/" + ShoppingList.currentListId + "/listItem/bulkAdd", {
			type: "POST",
			data: data,
			success: function (data) {
				data.forEach(function (item) {
					shoppingList.append(listItemTemplate(item)).trigger("create");
				});

				console.log("Items added");
			},
			error: function(xhr, status, error) {
				console.log("ERROR: " + error);
			}
		});

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

		$.ajax("shoppingList/" + ShoppingList.currentListId + "/listItem", {
			type: "POST",
			data: item,
			success: function (data) {
				shoppingList.append(listItemTemplate(data)).trigger("create");
				console.log("Item added");
			},
			error: function (xhr, status, error) {
				console.log("ERROR: " + error);
			}
		});
	});
});
