package shopping.list

class ListItemController {

	def index() { }

	def update() {
		def item = ListItem.get(params.id)

		if (!item) {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}

			return
		}

		item.obtained = request.JSON?.obtained

		if (!item.save()) {
			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		}

		render(status: 204, text: '')
	}

	def save() {
		def shoppingList = ShoppingList.get(params.sid)

		if (!shoppingList) {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}
		}

		params.remove('sid')


		println request.JSON

		def item = new ListItem(request.JSON)

		shoppingList.addToItems(item)

		if (!shoppingList.save()) {

			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		} else {
			shoppingList.errors.each { println it }
			item.errors.each {
				println "Item: " + it
			}
		}

		def output = [
			id: item.id,
			name: item.name,
			quantity: item.quantity,
			obtained: item.obtained
		]

		render(contentType: "application/json") {
			output
		}
	}

	def show() {
		def item = ListItem.get(params.id)

		if (item) {
			def output = [
				id: item.id,
				name: item.name,
				quantity: item.quantity,
				obtained: item.obtained
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
}
