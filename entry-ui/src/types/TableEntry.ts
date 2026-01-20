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

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}
