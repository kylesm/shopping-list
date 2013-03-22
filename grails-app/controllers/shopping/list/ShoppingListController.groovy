package shopping.list

class ShoppingListController {
	def show() {
		def shoppingList = ShoppingList.get(params.id)

		if (shoppingList) {
			def output = [
				description: shoppingList.description,
				dateCreated: shoppingList.dateCreated,
				lastUpdated: shoppingList.lastUpdated,
				completed: shoppingList.completed,
				items: shoppingList.items
			]

			render(contentType: "application/json") {
				output
			}
		} else {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}
		}
	}

	def update() {
		println "Received a request to update a shoppingList"
	}

	def list() {
		render(contentType: "application/json") {
			ShoppingList.findAll()
		}
	}
}
