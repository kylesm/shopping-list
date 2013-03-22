class UrlMappings {

	static mappings = {
		"/shoppingList/$sid/listItem/$id?"(controller: "listItem", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}

		"/shoppingList/$id?"(controller: "shoppingList", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
