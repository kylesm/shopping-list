class UrlMappings {

	static mappings = {
		"/shoppingList/$sid/listItem/bulkAdd"(controller: "listItem", parseRequest: true) {
			action = [POST: "bulkAdd"]
		}

		"/shoppingList/$sid/listItem/$id?"(controller: "listItem", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}

		"/shoppingList"(controller: "shoppingList", parseRequest: true) {
			action = [GET: "list", POST: "save"]
		}

		"/shoppingList/$id"(controller: "shoppingList", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete"]
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
