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

package shopping.list

class ListItemController {
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
			item.errors.each { log.error(it) }

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

		def item = new ListItem(request.JSON)
		item.order = shoppingList.items.size() + 1

		shoppingList.addToItems(item)

		if (!shoppingList.save()) {

			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		} else {
			shoppingList.errors.each { log.error(it) }
			item.errors.each { log.error(it) }
		}

		def output = [
			id: item.id,
			name: item.name,
			quantity: item.quantity,
			obtained: item.obtained
		]

		render(contentType: "application/json", status: 201) {
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

	def bulkAdd() {
		def shoppingList = ShoppingList.get(params.sid)

		if (!shoppingList) {
			render(contentType: "application/json", status: 404) {
				[errorMessage: "Not found"]
			}
		}

		params.remove('sid')

		def nextOrder = shoppingList.items.size() + 1
		def items = request.JSON?.items
		def pattern = ~/^(\d+)\s+(.*)\s*$/
		def newItems = []

		items.eachLine { line ->
			def matcher = pattern.matcher(line)
			def quantity = 1
			def name = line

			if (matcher.matches()) {
				quantity = matcher[0][1]
				name = matcher[0][2]
			}

			def item = new ListItem([order: nextOrder, name: name, quantity: quantity])
			shoppingList.addToItems(item)
			newItems << item
			nextOrder += 1
		}

		if (!shoppingList.save()) {
			shoppingList.errors.each { log.error(it) }

			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		}

		def output = []

		newItems.each { item ->
			output << [	
				id: item.id,
				name: item.name,
				quantity: item.quantity,
				obtained: item.obtained
			]
		}

		render(contentType: "application/json", status: 201) {
			output
		}
	}
}
