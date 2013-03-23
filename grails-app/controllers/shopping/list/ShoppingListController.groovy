package shopping.list

class ShoppingListController {
	def list() {
		render(contentType: "application/json") {
			ShoppingList.listOrderByLastUpdated(max: 25, order: "desc")
		}
	}

	def save() {
		def shoppingList = new ShoppingList(request.JSON)

		if (!shoppingList.save()) {
			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		} else {
			def output = [
				id: shoppingList.id,
				description: shoppingList.description,
				dateCreated: shoppingList.dateCreated,
				lastUpdated: shoppingList.lastUpdated
			]

			render(contentType: "application/json", status: 201) {
				output
			}
		}
	}

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

	def delete() {
		def shoppingList = ShoppingList.get(params.id)

		if (shoppingList) {
			shoppingList.delete()

			render(contentType: "application/json", status: 204, text: '')
		} else {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}
		}
	}

	def update() {
		def shoppingList = ShoppingList.get(params.id)

		if (!shoppingList) {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}

			return
		}

		shoppingList.description = request.JSON?.description

		if (!shoppingList.save()) {
			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		}

		render(status: 204, text: '')
	}
}
