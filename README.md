# Tree traversal playground 

```
The usage of the forest playground 
        tree constructors: 
                - newTree((<representation1>), (<representation2>))
                        - define a new tree and save it under a variable
                        - usage:
                                - <variableName> = newTree(<infixRepresentation1>, <representation2>)
                                        - the <variableName> cannot be empty
                                        - the <variableName> can consist of letters and numbers

                        - proper syntax of a tree representation: (pos|inf|pre)=<element>,<element>(...), 
                                - the <element> cannot be empty 
                                - no elements can repeat
                                - no ',' as element allowed
                                - both representations must contain the same characters 
                                - an infix and one postfix or prefix representation is required
                                - the first representation must be the infix
                                - examples of proper tree representation: 
                                        - pre=1,2,3,4,5,6,7
                                        - pos=a,b,c,d,e,f,g
                                        - inf=1,+,5,*,3,/,2
                                        - the representations can also be saved as variables:
                                                - rep(<variableName>)=<representation>
                                                - example of the proper representation declaration:
                                                        - rep(r1)=inf=42,2,3,4,5,6
                                                        - rep(r2)=pos=42,2,3,4,5,6

                        - example of proper tree definition:
                                - t = newTree((inf=a,42,c,d,e,f,g), (pre=42,f,e,d,a,c,g))
                                - t = newTree(rep(r1), rep(r2))
                - newRandomTree(<elements>)
                        - define a tree with the nodes listed in the elements 
                        - usage:
                                - <variableName> = newRandomTree(<elements>)
                                - the <element> cannot be empty 
                                - no elements can repeat
                                - no ',' as element allowed
                                - both representations must contain the same characters 
                                - an infix and one postfix or prefix representation is required
                                - examples of proper definition of a random tree: 
                                        - t = newRandomTree(42, b, c, d, e, f)

        utility functions:
                - <treeVariable>.draw()
                        - draw the tree in the console without any further information 

                - <treeVariable>.information()
                        - draw the tree and print the tree information in the console 

                - <treeVariable>.explain()
                        - assemble the tree and explain step by step how it was assembled 


demo program: 
>t = newRandomTree(a,b,c,d,e,f)
>t.information()

output: 
   (a)      
 /     \    
(e)   (c)   
    /     \ 
   (d)   (f)
 /          
(b)         
            

infix = eabdcf
postfix = ebdfca
prefix = aecdbf


tl;dr: 
        - new tree:      
                - t = newTree((inf=a,42,c,d,e,f,g), (pre=42,f,e,d,a,c,g))
                - t = newTree(rep(r1), rep(r2))
                        - if and only if r1 and r2 are defined and of the proper type (r1 = infix, r2 = postfix/prefix)
                - you need to define the infix and prefix or postfix
                - first representation must be the infix 

        -new representation
                 - rep(r1)=inf=42,6,7,4,3

        - newrandom tree:
                - t = newRandomTree(42, b, c, d, e, f)

        - t.draw():
                - draw the tree in the console

        - t.information():
                - draw the tree and print information 

        - t.explain():
                - explain how the tree was assembled
```