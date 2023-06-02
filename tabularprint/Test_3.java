package tabularprint;


/**
 *
 * @author Apostolos Demertzis
 */
public class Test_3 {
    public static void main(String[] args) {
        TabularPrint tp = new TabularPrint();
        
        tp.setTitle("Table Title");
        tp.showRowSeparators(true);
        tp.showHeaders(true);
        tp.showBorder(true);
        
        tp.addColumnSeparator("|");
        // |1| add the first column by calling directly the constructor
        tp.addColumn(13, "Header 1\nthis column is aligned right", 
                TabularPrint.HORIZONTAL_RIGHT, TabularPrint.VERTICAL_MIDDLE);
        
        // add a custom column separator.
        tp.addColumnSeparator("  ");
        
        // |2| add the second column by calling a reduced constructor
        tp.addColumn(20, "Header 2\nthis column is aligned left");
        
        // choose a different separator between column1 and column2
        tp.addColumnSeparator();
        
        // |3| add another column by chaining parameters
        tp.addColumn().header("Header 3\nit's a number").width(8).horizontalRight()
                .verticalMIddle().decimals(2);
        
        // the column separator 
        tp.addColumnSeparator();
        
        // |4| add a default column. That is 12 characters width with no header and left alignment
        tp.addColumn();
        tp.addColumnSeparator();

//        cp.setRowSeparatorCharacters('-');
        
        // pass data
        tp.printRow(1245, "second cell", 56.5825f, 
                "if the text does not fit within the column width then, " + 
                                                        "is is shared over multiple lines");
//        tp.setPaddingChar("^");
        tp.printRow(67890, "first line\nsecond line", 127.0256,
                "split\nat\nevery\nword");
        
        //last column is missing
        tp.printRow("some text here", 130.0311, "not a\nnumber");
        
        //more values than expected
        tp.printRow("1234567890123", 130.0311, 130.0311, "123456789012",
                1, 2);
        
        
        System.out.println(tp.getString());
    }
}
