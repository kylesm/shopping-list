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

class ShoppingListController {
	def list() {
		render(contentType: "application/json") {
			ShoppingList.listOrderByLastUpdated(max: 25, order: "desc")
		}
	}

	def save() {
		def shoppingList = new ShoppingList(request.JSON)

		if (!shoppingList.save()) {
			shoppingList.errors.each { log.error(it) }

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
			shoppingList.errors.each { log.error(it) }

			render(contentType: "application/json", status: 500) {
				[errorMessage: "Unable to save changes"]
			}

			return
		}

		render(status: 204, text: '')
	}
}
