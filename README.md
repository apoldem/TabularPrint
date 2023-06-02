# TabularPrint
`TabularPrint` is a single class that allows printing in tabular format, in other words, printing in columns. It supports the usual formatting properties (per column), namely, column width, horizontal and vertical alignment and number of decimals, in case of numeric data. Cells are automatically spread over multiple lines, if the cell’s length exceeds the column width.

`TabularPrint` has an extremely easy interface. We create a `TabularPrint` object

    TabularPrint tp = new TabularPrint();
	
We can add some columns, without any parameters

    tp.addColumn(); // first column
    tp.addColumn(); // second column
    tp.addColumn(); // third column

Columns without parameters are defined by their default values. These are: column width is set to 12 characters, vertical alignment is set to ‘top’ and horizontal alignment is set to ‘left’. Then, we just print the data, row by row

    tp.printRow(10,  "First Name", 123.45);
    tp.printRow( 11, "Second Name", 3210.54);
    tp.printRow(12,  "Third Name", 6.7890);
	
Finally, we get the resulting `String` by calling `tp.getString()` method:

    System.out.println(tp.getString());

The output of the above code is

    10          First Name  123.45      
    11          Second Name 3210.54     
    12          Third Name  6.789

## Passing some parameters, during column construction
During column construction, we can pass any property by chain invocation. We can also add column separator (the pipe symbol) between two adjacent columns. For example,

    tp.addColumn().width(4).horizontalCenter(); // first column
    tp.addColumnSeparator();
    tp.addColumn().width(15).horizontalLeft();  // second column
    tp.addColumnSeparator();
    tp.addColumn().horizontalRight();           // third column
    
gives the output

    10 │First Name     │      123.45
    11 │Second Name    │     3210.54
    12 │Third Name     │       6.789

## A nicer output
We could add a title and a border around the table. The following code

    TabularPrint tp = new TabularPrint();
    tp.showHeaders(true);
    tp.showBorder(true);
    tp.setTitle("Table Title");
        
    tp.addColumn().header("integer").width(4).horizontalCenter();
    tp.addColumnSeparator();
    tp.addColumn().header("name").width(15).horizontalLeft();
    tp.addColumnSeparator();
    tp.addColumn().header("decimal").horizontalRight().decimals(2);

    tp.printRow(10,  "First Name", 123.45);
    tp.printRow( 11, "Second Name", 3210.54);
    tp.printRow(12,  "Third Name", 6.7890);
    System.out.println(tp.getString());
    
gives the output

    ┌─────────────────────────────────┐
    │           Table Title           │
    ├────┬───────────────┬────────────┤
    │inte│name           │     decimal│
    │ ger│               │            │
    ├────┼───────────────┼────────────┤
    │ 10 │First Name     │      123,45│
    │ 11 │Second Name    │     3210,54│
    │ 12 │Third Name     │        6,79│
    └────┴───────────────┴────────────┘

Notice the header of the first column. It doesn’t fit into the column width, so,  it's spread over two lines. This is the standard behavior for all cells.

In addition, we have call `.decimals(2)` during the construction of the third column. That meansw that, numbers in this column are rounded to two decimals, before printing.
