/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray.data

import com.example.lunchtray.constants.ItemType
import com.example.lunchtray.model.MenuItem

/**
 * A data source for menu items.
 *
 * This data source provides a map of menu items, keyed by their name. Each menu item
 * has a name, description, price, and type. The type can be one of the following values:
 *
 * * `ENTREE`: An entree is a main course dish.
 * * `SIDE_DISH`: A side dish is a dish that is served with an entree.
 * * `ACCOMPANIMENT`: An accompaniment is a dish that is served with an entree or side dish.
 *
 * The data source is used by the menu fragments to display the menu items to the user.
 */
object DataSource {
    /**
     * A map of menu items, keyed by their name.
     */
    val menuItems = mapOf(
        "cauliflower" to
                MenuItem(
                    name = "Cauliflower",
                    description = "Whole cauliflower, brined, roasted, and deep fried",
                    price = 7.00,
                    type = ItemType.ENTREE
                ),
        "chili" to
                MenuItem(
                    name = "Three Bean Chili",
                    description = "Black beans, red beans, kidney beans, slow cooked, topped with onion",
                    price = 4.00,
                    type = ItemType.ENTREE
                ),
        "pasta" to
                MenuItem(
                    name = "Mushroom Pasta",
                    description = "Penne pasta, mushrooms, basil, with plum tomatoes cooked in garlic and " +
                            "olive oil",
                    price = 5.50,
                    type = ItemType.ENTREE
                ),
        "skillet" to
                MenuItem(
                    name = "Spicy Black Bean Skillet",
                    description = "Seasonal vegetables, black beans, house spice blend, served with avocado " +
                            "and quick pickled onions",
                    price = 5.50,
                    type = ItemType.ENTREE
                ),
        "salad" to
                MenuItem(
                    name = "Summer Salad",
                    description = "Heirloom tomatoes, butter lettuce, peaches, avocado, balsamic dressing",
                    price = 2.50,
                    type = ItemType.SIDE_DISH
                ),
        "soup" to
                MenuItem(
                    name = "Butternut Squash Soup",
                    description = "Roasted butternut squash, roasted peppers, chili oil",
                    price = 3.00,
                    type = ItemType.SIDE_DISH
                ),
        "potatoes" to
                MenuItem(
                    name = "Spicy Potatoes",
                    description = "Marble potatoes, roasted, and fried in house spice blend",
                    price = 2.00,
                    type = ItemType.SIDE_DISH
                ),
        "rice" to
                MenuItem(
                    name = "Coconut Rice",
                    description = "Rice, coconut milk, lime, and sugar",
                    price = 1.50,
                    type = ItemType.SIDE_DISH
                ),
        "bread" to
                MenuItem(
                    name = "Lunch Roll",
                    description = "Fresh baked roll made in house",
                    price = 0.50,
                    type = ItemType.ACCOMPANIMENT
                ),
        "berries" to
                MenuItem(
                    name = "Mixed Berries",
                    description = "Strawberries, blueberries, raspberries, and huckleberries",
                    price = 1.00,
                    type = ItemType.ACCOMPANIMENT
                ),
        "pickles" to
                MenuItem(
                    name = "Pickled Veggies",
                    description = "Pickled cucumbers and carrots, made in house",
                    price = 0.50,
                    type = ItemType.ACCOMPANIMENT
                )
    )
}
