import { useEffect } from 'react';
import { Container, Typography, CssBaseline } from '@mui/material';
import { EntryForm } from './components/EntryForm';
import { EntryTable } from './components/EntryTable';
import { useAppDispatch } from './store/hooks';
import { fetchEntries } from './store/entriesSlice';

function App() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchEntries());
  }, [dispatch]);

  return (
    <>
      <CssBaseline />
      <Container maxWidth="md" sx={{ py: 4 }}>
        <Typography variant="h4" sx={{ mb: 3 }}>
          Table Entry Manager
        </Typography>
        <EntryForm />
        <EntryTable />
      </Container>
    </>
  );
}

export default App;
