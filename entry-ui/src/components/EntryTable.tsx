import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { useAppSelector, useAppDispatch } from '../store/hooks';
import { deleteEntry } from '../store/entriesSlice';

export function EntryTable() {
  const dispatch = useAppDispatch();
  const { items, loading } = useAppSelector((state) => state.entries);

  if (loading) return <div>Loading...</div>;

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Number</TableCell>
            <TableCell>Selector</TableCell>
            <TableCell>Free Text</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {items.map((entry) => (
            <TableRow key={entry.id}>
              <TableCell>{entry.numberValue}</TableCell>
              <TableCell>{entry.selectorValue}</TableCell>
              <TableCell>{entry.freeText}</TableCell>
              <TableCell>
                <IconButton
                  size="small"
                  onClick={() => entry.id && dispatch(deleteEntry(entry.id))}
                >
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
