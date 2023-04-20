export interface IContact {
  id: number | null;
  firstName: string | null;
  lastName: string | null;
  phoneNumber: string | null;
}

export class Contact implements IContact {
  constructor(public id: number | null, public firstName: string | null, public lastName: string | null, public phoneNumber: string | null) {
  }
}
