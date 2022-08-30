This is a very basic game.
There are two main parts, an editor and the game.
This is my first game so don't expect anything mind-blowing.

Editor: Tiles
  To edit the tile portion of the map, you need to be viewing the tile section of the editor via the [ and/or ]  buttons.
  You can add tiles and delete tiles. 
  In order to add or delete tiles, you must have the respective toggle button on. Then you can click or drag the mouse on the map to add/delete tiles.
  To choose a tile, you can click the gallery button which will open a gallery. From there, you can find a tile, choose a tile, and exit.
  Tiles are added to a grid.
  
Editor: Items
  To edit the item portion of the map, you need to be viewing the item section of the editor via the [ and/or ] buttons.
  You can add items, delete items, move items, resize items, and manipulate the layering of items.
  To add items, the add-item toggle needs to be on, and an item needs to be selected. When you click the map, an item will be placed where the mouse was clicked.
  To delete items, you must select an item, and then press the DELETE key.
  To move items, you must select an item and then drag the mouse. The item will follow.
  To resize items, you must select an item and then drag the mouse. However, where you drag the mouse must start from one of the item's vertices.
  To manipulate the layering of an item, you must press the UP and DOWN keys. Pressing the UP key will bring the item one layer up and pressing the DOWN key will bring 
  item one layer down.
  
Editor: General
  You can toggle the grid on the map by pressing the G/g key.
  You can change the name of the map via the input box.
  You can save the map and reset the map.

Viewing Maps:
  From this page, you can view all of the saved maps and create a new map. If you click on a saved map, you can either play, edit, or delete it.
  If you create a new map, well you're creating a new map.
  When you click on a saved map, a bunch of actions (as previously mentioned) will appear. Once you hover out of that region, they'll disappear.

Playing a Map:
  Given an entity, you can explore the map you created. Keep in mind, this is nothing extraordinary. I will admit this portion is laggy because of the path-finding
  algorithm.
  Using the arrow keys, you can move the entity. If you press the SPACE bar the entity will jump.
  There is an NPC that follows the entity. I may or may not have commented this out because it was causing the game to slow down.
  There's also some camera work. When the entity gets near the edge of the screen, the camera will basically create more space. I tried my best to make it smooth.
  The camera happens both in the x- and y-directions, but the implementation in the y-direction is a bit over-the-top.
  
