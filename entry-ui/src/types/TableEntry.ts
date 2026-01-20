export interface TableEntry {
  id: number;
  numberValue: number;
  selectorValue: string;
  freeText: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateTableEntry {
  numberValue: number;
  selectorValue: string;
  freeText: string;
}
