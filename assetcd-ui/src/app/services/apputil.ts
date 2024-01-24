export default class CommonUtils {
    constructor() { }

  public static deepCopy(src: any) {
    return JSON.parse(JSON.stringify(src));
  }

  public static isEmpty = (inputObject: any) => {
      return Object.keys(inputObject).length === 0;
  };

  /*public hasRole(role: string): boolean {
    if (this.userDetail != null && this.userDetail.roles != null) {
        for (var j=0; j<this.userDetail.roles.length; j++) {
            //if (this.userDetail.roles[j].match(role)) return true;
            if (this.userDetail.roles[j] == role) return true;
        }
    } 
    return false;
  }

  private searchStringInArray (str: string, strArray: string[]) {
    for (var j=0; j<strArray.length; j++) {
        if (strArray[j].match(str)) return j;
    }
    return -1;
  }*/

}

