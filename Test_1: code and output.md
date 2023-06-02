# Test_1

### Code 1

        // the defualt values of the TabularPrint
        // --------------------------------------
        
        // create a TabularPrint object
        TabularPrint tp1 = new TabularPrint();

        // add columns
        // -----------
        
        // default width is 8 characters
        tp1.addColumn(); // first column
        tp1.addColumn(); // second column
        tp1.addColumn(); // third column
        tp1.addColumn(); // fourth column
        
        // print some rows
        // ---------------
        
        // string | integer | boolean | double
        tp1.printRow( "First Name", 2 , true, 2.45);
        // string instead of boolean
        tp1.printRow( "Second Name", 12, "false", 14.70);
        // first column contains a long name
        tp1.printRow("A very long name", 3, true, 1.0);
        
        System.out.println(tp1.getString());

and the output

    First Name  2           true        2.45        
    Second Name 12          false       14.7        
    A very long 3           true        1.0         
    name                                   

### Code 2: a nicer printing
        TabularPrint tp2 = new TabularPrint();

        tp2.addColumn(12, "Name");
        tp2.addColumn().width(8).header("Integer").horizontalRight();
        tp2.addColumn().width(6).header("Bool").horizontalCenter();
        tp2.addColumn().width(12).header("Decimal").horizontalRight().decimals(3);
        
        tp2.showHeaders(true);
        
        tp2.printRow( "First Name", 2 , true, 2.45);
        tp2.printRow( "Second Name", 12, "false", 14.70);
        tp2.printRow("A very long name", 3, true, 1.0);
        
        System.out.println(tp2.getString());

and the output

    Name         Integer Bool      Decimal
    ──────────────────────────────────────
    First Name         2 true        2,450
    Second Name       12 false      14,700
    A very long        3 true        1,000
    name                                  

### Code 3: an even nicer printing
        TabularPrint tp3 = new TabularPrint();
        
        tp3.addColumn().width(12).header("Name");
        tp3.addColumnSeparator();
        tp3.addColumn().width(8).header("Integer").horizontalRight();
        tp3.addColumnSeparator();
        tp3.addColumn().width(6).header("Bool").horizontalCenter();
        tp3.addColumnSeparator();
        tp3.addColumn().width(12).header("Decimal").horizontalRight().decimals(3);
        
        tp3.showHeaders(true);
        tp3.showBorder(true);
        
        tp3.printRow( "First Name", 2 , true, 2.45);
        tp3.printRow( "Second Name", 12, "false", 14.70);
        tp3.printRow("A very long name", 3, true, 1.0);
        
        System.out.println(tp3.getString());

and the output

    ┌────────────┬────────┬──────┬────────────┐
    │Name        │ Integer│ Bool │     Decimal│
    ├────────────┼────────┼──────┼────────────┤
    │First Name  │       2│ true │       2,450│
    │Second Name │      12│ false│      14,700│
    │A very long │       3│ true │       1,000│
    │name        │        │      │            │
    └────────────┴────────┴──────┴────────────┘

### Code 4: add a title and show row separators
        TabularPrint tp4 = new TabularPrint();
        
        tp4.addColumn().width(12).header("Name");
        tp4.addColumnSeparator();
        tp4.addColumn().width(8).header("Integer").horizontalRight();
        tp4.addColumnSeparator();
        tp4.addColumn().width(6).header("Bool").horizontalCenter();
        tp4.addColumnSeparator();
        tp4.addColumn().width(12).header("Decimal").horizontalRight().decimals(3);
        
        // set the title
        tp4.setTitle("This is the table title");
        tp4.showTitleSeparator(true);
        tp4.showHeaders(true);
        tp4.showBorder(true);
        tp4.showRowSeparators(true);
        
        // pass some data: a string and two numbers
        tp4.printRow( "First Name", 2 , true, 2.45);
        tp4.printRow( "Second Name", 12, "false", 14.70);
        tp4.printRow("A very long name", 3, true, 1.0);
        
        System.out.println(tp4.getString());

and the output

    ┌─────────────────────────────────────────┐
    │         This is the table title         │
    ├────────────┬────────┬──────┬────────────┤
    │Name        │ Integer│ Bool │     Decimal│
    ├────────────┼────────┼──────┼────────────┤
    │First Name  │       2│ true │       2,450│
    ├────────────┼────────┼──────┼────────────┤
    │Second Name │      12│ false│      14,700│
    ├────────────┼────────┼──────┼────────────┤
    │A very long │       3│ true │       1,000│
    │name        │        │      │            │
    └────────────┴────────┴──────┴────────────┘
	
