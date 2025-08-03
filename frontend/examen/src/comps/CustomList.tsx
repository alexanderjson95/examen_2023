/*
    We define the elements/logic that can be added to the component. 
    In this case we require a list of strings, as it is a list. 
    We also allow any type of element to be added to the component if needed. 
*/


// Here we define datatypes we would accept in a list, should reflect database
type ListItem = {
    num?: number,
    text?: string,
    bool?: boolean,
    date?: Date,
}

type ListProp = {
  //items: ListItem[];
  items: string[];
  children?: React.ReactNode; // ReactNode = all html elements react compiler can parse
  

  /*
   children is a reserved name for React. By specifically using a reserved name, the React compiler knows that child components are 
   to be expected in here. This means the React automatically injects the elements as children props when we add them. 
  */

}



// maybe map both items and children for different usecases? check if else?
export const CustomList = ({ items, children }: ListProp) => {
  return (
    <>
      <ul>
        {items.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
      {children}
    </>
  );
};
