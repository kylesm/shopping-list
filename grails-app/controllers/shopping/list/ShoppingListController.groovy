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
	def springSecurityService
	def authorizationService

	private def checkListOwnership(id) {
		def response = authorizationService.checkListOwnership(id)

		if (!response.authorized) {
			render(contentType: "application/json", status: response.status) {
				[errorMessage: response.errorMessage]
			}

			return false
		}

		return true
	}

	def list() {
		render(contentType: "application/json") {
			ShoppingList.findAllByOwner(springSecurityService.currentUser, [max: 25, sort: "lastUpdated", order: "desc"])
		}
	}

	def save() {
		def shoppingList = new ShoppingList(request.JSON)
		shoppingList.owner = springSecurityService.currentUser

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
		if (!checkListOwnership(params.id)) {
			return
		}

		def shoppingList = ShoppingList.get(params.id)

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
	}

	def delete() {
		if (!checkListOwnership(params.id)) {
			return
		}

		def shoppingList = ShoppingList.get(params.id)

		shoppingList.delete()

		render(contentType: "application/json", status: 204, text: '')
	}

	def update() {
		if (!checkListOwnership(params.id)) {
			return
		}

		def shoppingList = ShoppingList.get(params.id)

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
