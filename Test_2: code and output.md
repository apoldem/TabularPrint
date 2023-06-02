# Test_2

### Code 1: vertical allignmet top
        TabularPrint tp1 = new TabularPrint();

        // vertical alligment top is the standard behavior
        tp1.addColumn().width(12).horizontalLeft().header("header 1");
        // add a custom column separator (3 spaces)
        tp1.addColumnSeparator("   ");
        tp1.addColumn().width(17).horizontalCenter().header("header 2");
        tp1.addColumnSeparator("   ");
        tp1.addColumn().width(17).horizontalRight().header("header 3");
        
        tp1.showHeaders(true);
        
        tp1.printRow( "The\nsound\nof\nocean\nwaves\ncalm\nmy\nsoul… ",
                "The sound of ocean waves calm my soul…", 
                "A short text");
        
        System.out.println(tp1.getString());

and the output

    header 1            header 2                header 3
    ────────────────────────────────────────────────────
    The            The sound of ocea        A short text
    sound          n waves calm my s                    
    of                    oul…                          
    ocean                                               
    waves                                               
    calm                                                
    my                                                  
    soul…                                               

### Code 2: vertical allignmet middle
        TabularPrint tp2 = new TabularPrint();

        tp2.addColumn().width(12).horizontalLeft().verticalMIddle().header("left & \nmiddle");
        tp2.addColumn().width(17).horizontalCenter().verticalMIddle().header("center & middle");
        tp2.addColumn().width(17).horizontalRight().verticalMIddle().header("right & middle");
        
        tp2.showBorder(true);
        tp2.showHeaders(true);
        
        tp2.printRow( "The\nsound\nof\nocean\nwaves\ncalm\nmy\nsoul… ",
                "The sound of ocean waves calm my soul…", 
                "A short text");
        
        System.out.println(tp2.getString());

and the output

    ┌──────────────────────────────────────────────┐
    │left &       center & middle    right & middle│
    │middle                                        │
    ├──────────────────────────────────────────────┤
    │The                                           │
    │sound                                         │
    │of          The sound of ocea                 │
    │ocean       n waves calm my s     A short text│
    │waves              oul…                       │
    │calm                                          │
    │my                                            │
    │soul…                                         │
    └──────────────────────────────────────────────┘

### Code 3: vertical allignmet bottom
        TabularPrint tp3 = new TabularPrint();

        tp3.addColumn().width(12).horizontalLeft().verticalBottom().header("left & \nbottom");
        tp3.addColumnSeparator();
        tp3.addColumn().width(17).horizontalCenter().verticalBottom().header("center & bottom");
        tp3.addColumnSeparator();
        tp3.addColumn().width(17).horizontalRight().verticalBottom().header("right & bottom");
        
        tp3.showBorder(true);
        tp3.showHeaders(true);
        
        tp3.printRow( "The\nsound\nof\nocean\nwaves\ncalm\nmy\nsoul… ",
                "The sound of ocean waves calm my soul…", 
                "A short text");
        
        System.out.println(tp3.getString());

and the output

    ┌────────────┬─────────────────┬─────────────────┐
    │left &      │ center & bottom │   right & bottom│
    │bottom      │                 │                 │
    ├────────────┼─────────────────┼─────────────────┤
    │The         │                 │                 │
    │sound       │                 │                 │
    │of          │                 │                 │
    │ocean       │                 │                 │
    │waves       │                 │                 │
    │calm        │The sound of ocea│                 │
    │my          │n waves calm my s│                 │
    │soul…       │       oul…      │     A short text│
    └────────────┴─────────────────┴─────────────────┘
