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

class ListItem implements Comparable {
	String name
	int quantity
	boolean obtained
	Integer order = 1

	static belongsTo = ShoppingList

    static constraints = {
		name size: 3..64, blank: false, nullable: false
		quantity min: 1, nullable: false
    }

	static mapping = {
		order column: "item_order"
	}

	int compareTo(other) {
		return order.compareTo(other.order)
	}
}
