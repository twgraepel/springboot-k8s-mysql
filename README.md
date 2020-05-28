# Synthetic Transactions

## Synthetic Transaction State Flow
![alt text](readme_resources/SynthTransactionStateFlow.png "Synthetic Transaction State Flow")

### States
Status | Final State | Description
------ | ----------- | ----------------------------------
Initiate | &#9744; | XXXX
Running | &#9744; | XXXX
Complete | &#9745; | XXXX
Late | &#9744; | XXXX
Orphaned | &#9744; | XXXX
Unorphaned | &#9744; |  XXXX
Abandoned | &#9745; | XXXX

### Fields of Importance
Field Name | Data Type | In Database | In REST | Description
---------- | --------- | ----------- | ------- | -----------
id | UUID | &#9745; | &#9745; | This is the main type for the processing of the synthetic transaction.
synth_type | String | &#9745; | &#9745; | This is the main type for the processing of the synthetic transaction.
other_field_that_is_long |



# springboot-k8s-mysql

# This is an <h1> tag
## This is an <h2> tag
###### This is an <h6> tag

*This text will be italic*
_This will also be italic_

**This text will be bold**
__This will also be bold__

_You **can** combine them_

| Unchecked | Checked |
| --------- | ------- |
| &#9744;   | &#9745; |

Unordered lists
* Item 1
* Item 2
  * Item 2a
  * Item 2b
    * Yep really in there

Ordered Lists
1.  Hello
1.  There
    1. Tom
    1. Maureen

First Header | Second Header
------------ | -------------
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column
